package life.qbic.portal.sampletracking.view.samples;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.components.grid.HeaderRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import life.qbic.portal.sampletracking.data.SampleRepository;
import life.qbic.portal.sampletracking.view.ResponsiveGrid;
import life.qbic.portal.sampletracking.view.samples.SampleView.ProjectViewRequestedListener.ProjectViewRequested;
import life.qbic.portal.sampletracking.view.samples.viewmodel.Sample;

public class SampleView extends SampleDesign {

  private final ResponsiveGrid<Sample> sampleGrid;

  private final SampleRepository sampleRepository;

  private final SampleStatusComponentProvider sampleStatusComponentProvider;

  private final List<ProjectViewRequestedListener> projectViewRequestedListeners = new ArrayList<>();

  private String projectCode;

  public SampleView(SampleRepository sampleRepository, SampleStatusComponentProvider sampleStatusComponentProvider) {
    this.sampleRepository = sampleRepository;
    this.sampleStatusComponentProvider = sampleStatusComponentProvider;
    avoidElementOverlap();
    sampleGrid = createSampleGrid();
    addSampleGrid();
    setHeaderRowStyle();
    fillFilterDropdown();
    addSampleFilter();
    listenToViewSwitchingButton();
  }

  private void listenToViewSwitchingButton() {
    projectsButton.addClickListener(
        it -> fireProjectViewRequestedListener(new ProjectViewRequested()));
  }

  private void fillFilterDropdown() {
    statusComboBox.setItems("METADATA_REGISTERED",
        "SAMPLE_RECEIVED",
        "SAMPLE_QC_FAIL",
        "SAMPLE_QC_PASS",
        "LIBRARY_PREP_FINISHED",
        "DATA_AVAILABLE");
    statusComboBox.setSizeUndefined();
  }

  public void setProjectCode(String projectCode) {
    if (Objects.nonNull(this.projectCode) && this.projectCode.equals(projectCode)) {
      return;
    }
    this.projectCode = projectCode;
    if (this.isAttached()) {
      searchField.clear();
      statusComboBox.clear();
      loadSamplesForProject(projectCode);
    }
  }

  private void addSampleGrid() {
    this.addComponent(sampleGrid);
  }

  private void avoidElementOverlap() {
    this.addStyleName("responsive-grid-layout");
    this.setWidthFull();
  }

  private ResponsiveGrid<Sample> createSampleGrid() {
    ResponsiveGrid<Sample> grid = new ResponsiveGrid<>();
    grid.setDataProvider(new ListDataProvider<>(new ArrayList<>()));
    grid.addColumn(Sample::label)
        .setId("label")
        .setCaption("Sample Label");
    grid.addColumn(Sample::code)
        .setId("code")
        .setCaption("QBiC Code");
    grid.addComponentColumn(sampleStatusComponentProvider::getForSample)
        .setId("status")
        .setHandleWidgetEvents(true)
        .setCaption("Status");
    grid.setSizeFull();
    grid.setSelectionMode(SelectionMode.NONE);
    return grid;
  }



  private void setHeaderRowStyle() {
    HeaderRow headerRow = sampleGrid.getDefaultHeaderRow();
    headerRow.getCell("label").setStyleName("cell-min-width");
    headerRow.getCell("code").setStyleName("cell-min-width");
    headerRow.getCell("status").setStyleName("cell-min-width");
  }

  @Override
  public void attach() {
    super.attach();
    loadSamplesForProject(projectCode);
  }

  private void loadSamplesForProject(String projectCode) {
    sampleGrid.setItems(sampleRepository.findAllSamplesForProject(projectCode));
  }

  private void addSampleFilter() {
    sampleGrid.setFilter(new SampleFilter());
    searchField.setValueChangeMode(ValueChangeMode.LAZY);
    searchField.addValueChangeListener(
        it -> {
          SampleFilter gridFilter = (SampleFilter) sampleGrid.getGridFilter();
          sampleGrid.setFilter(gridFilter.containingText(it.getValue()));
        });
    statusComboBox.addValueChangeListener(
        it -> {
          SampleFilter gridFilter = (SampleFilter) sampleGrid.getGridFilter();
          sampleGrid.setFilter(gridFilter.withStatus(it.getValue()));
        });
  }

  public void addProjectViewRequestedListener(ProjectViewRequestedListener listener) {
    if (projectViewRequestedListeners.contains(listener)) {
      return;
    }
    projectViewRequestedListeners.add(listener);
  }

  public void removeProjectViewRequestedListener(ProjectViewRequestedListener listener) {
    projectViewRequestedListeners.remove(listener);
  }

  private void fireProjectViewRequestedListener(ProjectViewRequested event) {
    projectViewRequestedListeners.forEach(it -> it.onProjectViewRequested(event));
  }

  @FunctionalInterface
  public interface ProjectViewRequestedListener {

    class ProjectViewRequested {

    }

    void onProjectViewRequested(ProjectViewRequested event);

  }
}
