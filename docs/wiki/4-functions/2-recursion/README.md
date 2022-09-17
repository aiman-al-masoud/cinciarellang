# Recursion

Since all functions in Cinciarellang are anonymous, and since they are also pure, you cannot reliably call their name from within their body.

This is because their name isn't defined in their inner scope, it's defined in the outer scope, which a pure function cannot read. And since "their name" is just one (or more) reference to an object in RAM, it can't be bound to their internal environment once and for all.

This is why 