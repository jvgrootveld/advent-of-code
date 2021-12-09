# Manual deduct
- There are 4 unique lengths to start (1, 4, 7, 8)

```
  1:      4:      7:      8:   
 ....    ....    aaaa    aaaa  
.    c  b    c  .    c  b    c 
.    c  b    c  .    c  b    c 
 ....    dddd    ....    dddd  
.    f  .    f  .    f  e    f 
.    f  .    f  .    f  e    f 
 ....    ....    ....    gggg  
```
- Of the group of 5 letters we can determine the letter 3
  by checking which group had the same letters as '1'
```
   2:      3:      5:  
  aaaa    aaaa    aaaa 
 .    c  .    c  b    .
 .    c  .    c  b    .
  dddd    dddd    dddd 
 e    .  .    f  .    f
 e    .  .    f  .    f
  gggg    gggg    gggg 
```

- Of the leftovers of the group with 5 letters we can
  determine 2 and 5 by checking matches with '4'
    - 2 = 2 matches
    - 5 = 3 matches
```
   2:      5:  
  aaaa    aaaa 
 .    c  b    .
 .    c  b    .
  dddd    dddd 
 e    .  .    f
 e    .  .    f
  gggg    gggg 
```

- Of the group of 6 letters we can determine the letter 6
  by checking which group has only 2 matches with '7'
```
  0:       6:      9:
 aaaa     aaaa    aaaa
b    c   b    .  b    c
b    c   b    .  b    c
 ....     dddd    dddd
e    f   e    f  .    f
e    f   e    f  .    f
 gggg     gggg    gggg
```

- Of the leftovers of the group with 6 letters we can
  determine 0 and 9 by checking matches with '4'
  - 0 = 3 matches
  - 9 = 4 matches
```
  0:       9:
 aaaa     aaaa
b    c   b    c
b    c   b    c
 ....     dddd
e    f   .    f
e    f   .    f
 gggg     gggg
```