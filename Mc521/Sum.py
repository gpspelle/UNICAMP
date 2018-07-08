n = int(input())
ans = 0
t = abs(n)
l = list(range(1, t+1))
ans = sum(l)
if n > 0:
    print(ans)
else:
    ans = - ans
    ans += 1
    print(ans)

