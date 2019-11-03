package befaster.solutions.CHL;

import befaster.runner.SolutionNotImplementedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckliteSolution {
    public Integer checklite(String skus) {
        throw new SolutionNotImplementedException();
    }

    private Map<String, Long> getQuantityPerItems(String skus) {
        // we assume that the sku is a comma separate list of items
        List<String> items = Arrays.asList(skus.split(","));

        return items.stream()
            .collect(Collectors.groupingBy(s -> s, Collectors.joining()));
    }
}


