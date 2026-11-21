package use_case.view_summary;

/*
 * ViewSummaryInputBoundary
 * -------------------------
 * Defines the contract that the View Summary interactor exposes to its caller
 * (the controller).  Implementations handle the business logic for computing
 * time totals per activity and category for a given period.
 *
 * Main function:
 *  - execute(request) : view summary for a period (daily/weekly/monthly)
 */
public interface ViewSummaryInputBoundary {

    /**
     * Executes the view summary use case with the provided request data.
     *
     * @param request the view summary request containing username and period
     */
    void execute(ViewSummaryRequestData request);
}
