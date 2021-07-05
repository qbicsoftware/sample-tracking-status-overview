package life.qbic.portal.sampletracking

import com.vaadin.ui.VerticalLayout
import life.qbic.portal.sampletracking.components.projectoverview.ProjectOverviewView
import life.qbic.portal.sampletracking.components.projectoverview.ProjectOverviewViewModel
import life.qbic.portal.sampletracking.resource.project.ProjectResourceService

/**
 * <h1>Class that manages all the dependency injections and class instance creations</h1>
 *
 * <p>This class has access to all classes that are instantiated at setup. It is responsible to construct
 * and provide every instance with it's dependencies injected. The class should only be accessed once upon
 * portlet creation and shall not be used later on in the control flow.</p>
 *
 * @since 1.0.0
 *
*/
class DependencyManager {
    VerticalLayout portletView

    private ProjectResourceService projectResourceService

    DependencyManager() {
        setupServices()
        portletView = setupPortletView()
    }

    private void setupServices() {
        this.projectResourceService = new ProjectResourceService()
    }

    VerticalLayout getPortletView() {
        return portletView
    }

    private VerticalLayout setupPortletView() {
        ProjectOverviewView projectOverviewView = createProjectOverviewView()
        return projectOverviewView
    }

    /**
     * Creates a new ProjectOverviewView using
     * <ul>
     *     <li>{@link #projectResourceService}</li>
     * </ul>
     * @return a new ProjectOverviewView
     */
    private createProjectOverviewView() {
        ProjectResourceService projectResourceService = this.projectResourceService
        ProjectOverviewViewModel projectOverviewViewModel = new ProjectOverviewViewModel(projectResourceService)
        return new ProjectOverviewView(projectOverviewViewModel)
    }
}