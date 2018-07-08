#!/usr/bin/env python
# -*- coding: utf-8 -*-


n = int(input())

while(1):
	if n == 0:
		break;
	
	while n > 9:
		l = []
		while n != 0:
			r = n%10
			l.append(r)
			#print(n)
			
			n = int(n//10)
			#print(n)
		n = sum(l)
	
	print(n)
	n = int(input())

