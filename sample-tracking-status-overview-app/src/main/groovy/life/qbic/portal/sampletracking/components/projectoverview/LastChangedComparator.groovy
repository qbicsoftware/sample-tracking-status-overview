package life.qbic.portal.sampletracking.components.projectoverview

import java.time.Instant
import java.util.Comparator

/**
 * Compares ProjectSummaries by the last change time as denoted by the timestamp
 *
 * @since 1.0.0
 *
 */
class LastChangedComparator implements Comparator<ProjectSummary> {

  enum SortOrder {ASCENDING, DESCENDING}
  
  SortOrder sortOrder
  
  LastChangedComparator(SortOrder sortOrder) {
    this.sortOrder = sortOrder
  }

  @Override
  int compare(ProjectSummary o1, ProjectSummary o2) {
    int compare = Math.signum(o1.lastChanged.compareTo(o2.lastChanged))
    
    if (sortOrder == SortOrder.ASCENDING) {
      return compare
    } else {
      return compare * (-1)
    }
  }
}
