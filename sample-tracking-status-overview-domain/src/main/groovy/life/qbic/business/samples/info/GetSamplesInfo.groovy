package life.qbic.business.samples.info

import life.qbic.business.DataSourceException
import life.qbic.business.samples.download.DownloadSamplesDataSource
import life.qbic.datamodel.samples.Status

/**
 * <b>Download samples</b>
 *
 * <p>This use case returns samples of a project that have available data attached.</p>
 *
 * @since 1.0.0
 */
class GetSamplesInfo implements GetSamplesInfoInput {
  
  private final DownloadSamplesDataSource samplesDataSource
  private final GetSamplesInfoDataSource infoDataSource
  private final GetSamplesInfoOutput output

  /**
   * Default constructor for this use case
   * @param dataSource the data source to be used
   * @param output the output to where results are published
   * @since 1.0.0
   */
  GetSamplesInfo(DownloadSamplesDataSource samplesDataSource, GetSamplesInfoDataSource infoDataSource, GetSamplesInfoOutput output) {
    this.samplesDataSource = samplesDataSource
    this.infoDataSource = infoDataSource
    this.output = output
  }

  /**
   * This method calls the output interface with the codes of the samples in a project that have data attached.
   * In case of failure the output interface failure method is called.
   * @since 1.0.0
   */
  @Override
  void requestSampleInfosFor(String projectCode, Status status) {
    try {
        def sampleCodes = samplesDataSource.fetchSampleCodesFor(projectCode, status)
        println "first succeeded"
        def sampleCodesToNames = infoDataSource.fetchSampleNamesFor(sampleCodes)
        println "second succeeded"
        
      output.samplesWithNames(projectCode, status, sampleCodesToNames)
    } catch (DataSourceException dataSourceException) {
      output.failedExecution(dataSourceException.getMessage())
    } catch (Exception e) {
      output.failedExecution("Could not fetch sample codes with available data.")
    }
  }
}
