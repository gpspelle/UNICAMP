t = int(input())
n = int(input())
old_n = n
s = 0

while n:
    n -= 1

    s += int(input())

print((t) * (old_n +1 ) - s)
