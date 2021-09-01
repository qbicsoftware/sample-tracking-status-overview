package life.qbic.business.samples.count

/**
 * <b>Input interface for the {@link CountSamples} feature</b>
 *
 * <p>Provides methods to trigger the counting of samples for one project</p>>
 *
 * @since 1.0.0
 */
interface CountSamplesInput {

    /**
     * This method calls the output interface with the number of samples in the project
     * and the number of samples that have been received in the sequencing lab.
     * In case of failure the output interface failure method is called.
     *
     * @param projectCode a code specifying the samples that should be considered
     * @since 1.0.0
     */
    void countReceivedSamples(String projectCode)

    /**
     * This method calls the output interface with the number of samples in the project
     * and the number of samples that have failed quality control.
     * In case of failure the output interface failure method is called.
     *
     * @param projectCode a code specifying the samples that should be considered
     * @since 1.0.0
     */
    void countQcFailedSamples(String projectCode)

    /**
     * This method calls the output interface with the number of samples in the project
     * and the number of samples for which data is available.
     * In case of failure the output interface failure method is called.
     *
     * @param projectCode A code specifying the samples that should be considered
     * @since 1.0.0
     */
    void countAvailableDataSamples(String projectCode)

    /**
     * This method calls the output interface with the number of samples in the project
     * and the number of samples for which the library prep is finished.
     * In case of failure the output interface failure method is called.
     *
     * @param projectCode A code specifying the samples that should be considered
     * @since 1.0.0
     */
    void countLibraryPrepFinishedSamples(String projectCode)
}
