import sys

dp = [0] * 1009

dp[0] = 1

for i in range(1001):
    for j in range(0, 4):
        dp[i+1+(j%3)] += dp[i]

data = sys.stdin.readlines()
for i in data:
    num = int(i.strip())
    print(dp[num])
    
