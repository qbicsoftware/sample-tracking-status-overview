package life.qbic.portal.sampletracking

import com.vaadin.ui.VerticalLayout
import life.qbic.portal.sampletracking.components.projectoverview.ProjectOverviewView
import life.qbic.portal.sampletracking.components.projectoverview.ProjectOverviewViewModel

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

    DependencyManager() {
        ProjectOverviewViewModel viewModel = new ProjectOverviewViewModel()
        portletView = new ProjectOverviewView(viewModel)
    }

    VerticalLayout getPortletView() {
        return portletView
    }
}