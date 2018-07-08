#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#


n = int(input())
while n !=0:
	l = list(map(int,input().split()))
	l.sort()
	print (*l, sep=' ')
	n = int(input())

