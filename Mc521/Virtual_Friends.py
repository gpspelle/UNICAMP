#!/usr/bin/env python3
# -*- coding: utf-8 -*-

n = int(input())

for cc in range(n):
    f = int(input())
    d = {}
    n1, n2 = input().split()
    s = set()
    s.add(n1)
    s.add(n2)
    l = []
    l.append(s)
    print(len(l[0]))

    for i in range(f - 1):
        n1, n2 = input().split()
        f1 = f2 = None
        for k in range(len(l)):
            if n1 not in l[k] and n2 not in l[k]:
                pass
            elif n1 in l[k]:
                f1 = k
            elif n2 in l[k]:
                f2 = k
            if f1 and f2:
                break
        if f1 != f2 and f1 is not None and f2 is not None:
            l[f1] = l[f1].union(l[f2])
            print(len(l[f1]))
            l.remove(l[f2])
            # print("a")
        elif f1 is not None and f2 is None:
            l[f1].add(n2)
            print(len(l[f1]))
            # print("b")
        elif f2 is not None and f1 is None:
            l[f2].add(n1)
            # print(l[f2])
            print(len(l[f2]))
            # print("c")
        elif f1 is None and f2 is None:
            s = set()
            s.add(n1)
            s.add(n2)
            l.append(s)
            # print (l)
            print(len(l[-1]))
            # print("d")


