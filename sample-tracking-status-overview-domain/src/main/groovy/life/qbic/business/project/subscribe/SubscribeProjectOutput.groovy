package life.qbic.business.project.subscribe

/**
 * <b><short description></b>
 *
 * <p><detailed description></p>
 *
 * @since <version tag>
 */
interface SubscribeProjectOutput {

    /**
     * A subscription was added for a given project
     * @param project the project code of the subscribed project
     * @since 1.1.0
     */
    void subscriptionAdded(String project)

    /**
     * A subscription was not possible
     * @param firstName the first name of the subscriber
     * @param lastName the last name of the subscriber
     * @param email the email address of the subscriber
     * @param projectCode the project code to subscribe to
     */
    void subscriptionFailed(String firstName, String lastName, String email, String projectCode)
}