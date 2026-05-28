# Cognitive Complexity Victim

Small Java project intentionally written with high cognitive complexity.

Use it as the target workspace project for `SoftwareCognitiveComplexityReducer`.
The methods in `LegacyOrderProcessor` and `RiskReportGenerator` contain nested
conditionals, loops, repeated validation blocks, and long decision trees so the
tool has obvious candidates for Extract Method refactorings.

Suggested program arguments for the reducer:

```text
CognitiveComplexityVictim ILP true
```
