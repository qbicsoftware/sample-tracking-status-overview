package life.qbic.portal.sampletracking.components.projectoverview.samplelist

import com.vaadin.data.provider.DataProvider
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.*
import life.qbic.business.samples.info.GetSamplesInfoOutput
import life.qbic.datamodel.samples.Status
import life.qbic.portal.sampletracking.communication.notification.NotificationService

/**
 * <b>Shows the failed QC samples </b>
 *
 * @since 1.0.0
 */
class FailedQCSamplesView extends VerticalLayout {
    private final ViewModel viewModel
    private final Presenter presenter

    private Grid<Sample> samplesGrid
    private HorizontalLayout controls

    FailedQCSamplesView(NotificationService notificationService) {
        this.viewModel = new ViewModel()
        this.presenter = new Presenter(viewModel, notificationService)
        initLayout()
    }

    private void initLayout() {
        this.setMargin(false)
        this.setCaption("Samples that failed quality control")
        createControls()
        createSamplesGrid()
        this.addComponents(controls, samplesGrid)
    }

    /**
     * Resets the view to its initial state.
     * @since 1.0.0
     */
    void reset() {
        if (viewModel.samples.size() > 0) {
            viewModel.samples.clear()
        }
    }

    private void createSamplesGrid() {

        this.samplesGrid = new Grid<>()
        samplesGrid.addColumn(Sample::getCode).setCaption("Sample Code").setId("SampleCode")
        samplesGrid.addColumn(Sample::getTitle).setCaption("Sample Title").setId("SampleTitle")
        samplesGrid.setSelectionMode(Grid.SelectionMode.NONE)
        samplesGrid.setDataProvider(DataProvider.ofCollection(viewModel.getSamples()))
    }

    private void createControls() {
        controls = new HorizontalLayout()
        Button closeButton = setupCloseButton()
        this.controls.addComponent(closeButton)
        this.controls.setComponentAlignment(closeButton, Alignment.TOP_LEFT)
    }

    private Button setupCloseButton() {
        Button closeButton = new Button("Hide", VaadinIcons.CLOSE)
        closeButton.addClickListener({
            this.setVisible(false)
        })
        return closeButton
    }

    GetSamplesInfoOutput getPresenter() {
        return this.presenter
    }

    private static class Sample {
        final String code
        final String title

        Sample(String code, String title) {
            this.code = code
            this.title = title
        }
    }

    private static class ViewModel {
        List<Sample> samples = new ArrayList<>()
    }

    private static class Presenter implements GetSamplesInfoOutput {

        private final ViewModel viewModel
        private final NotificationService notificationService


        Presenter(ViewModel viewModel, NotificationService notificationService) {
            this.viewModel = viewModel
            this.notificationService = notificationService
        }

        @Override
        void failedExecution(String reason) {
            notificationService.publishFailure(reason)
        }

        /**
         * To be called after successfully fetching sample codes with respective sample names for the provided project and status.
         * @param projectCode the code of the project samples should be returned for
         * @param status the status of the samples that should be returned
         * @param sampleCodesToNames list of sample codes with names
         * @since 1.0.0
         */
        @Override
        void samplesWithNames(String projectCode, Status status, Map<String, String> sampleCodesToNames) {
            if (status == Status.SAMPLE_QC_FAIL) {
                List<Sample> samples = parseSamples(sampleCodesToNames)
                viewModel.samples.clear()
                viewModel.samples.addAll(samples)
            } else {
                //there is not behaviour defined so do nothing
            }
        }

        private static List<Sample> parseSamples(Map<String, String> codesToNames) {
            List<Sample> samples = codesToNames.entrySet().stream()
                    .map({
                        return new Sample(it.key, it.value)
                    }).collect()
            return Optional.ofNullable(samples).orElse([])
        }
    }
}
