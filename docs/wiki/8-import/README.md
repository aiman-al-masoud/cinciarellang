# Import

File imports work as you'd expect them to:


```
// this is in: /home/foo/bar/file1.ci

import Duck from 'file2.ci';
import Util from 'lib/file3.ci';

```

```
// this is in: /home/foo/bar/file2.ci

Duck = class {
    quack = \ -> 'quack quack';
}

```


```
// this is in: /home/foo/bar/lib/file3.ci

Util = class {
    f = \x, y -> x+y;
}

```

