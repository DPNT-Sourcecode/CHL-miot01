package befaster.solutions.HLO;

import befaster.runner.SolutionNotImplementedException;

public class HelloSolution {
    public String hello(String friendName) {
        return
            new StringBuilder()
                .append("Hello, ")
                .append(friendName)
                .append("!")
                .toString();
    }
}
