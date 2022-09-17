# Pure Functions

All Cinciarellang functions are *pure*, unless explicitly made impure.

A *pure function* doesn't produce any side effects, and its output (return value) only depends on its input (parameters), this means that it **cannot** under any circumstance:

* Read from (or write to) the outer scope.
* Modify its input paramters.
