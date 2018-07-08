import math
from sys import stdin

line = stdin.readline()
while True:
	if len(line) <= 4:
		break
	p, q, r, s, t, u = map(int, line.split())

	found = False
	inic = 0.0
	end = 1.0
	while end - inic >= 0.000000001:
		i = (end + inic) / 2.0

		bisec = math.exp(-i) * p + math.sin(i) * q + math.cos(i) * r + math.tan(i) * s + i * i * t + u
		
		if bisec < 0:
			end = (end + inic) / 2.0
		else:
			inic = (end + inic) / 2.0
			
	if abs(math.exp(-(end+inic)/2.0) * p + math.sin((end+inic)/2.0) * q + math.cos((end+inic)/2.0) * r + math.tan((end+inic)/2.0) * s + (end+inic)/2.0 * (end+inic)/2.0 * t + u) <= 0.0001:
		found = True
		print("%.4f" % i)

	if not found:
		print("No solution")

	line = stdin.readline()
