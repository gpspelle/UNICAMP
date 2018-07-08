n = int(input())
total = 0
t1 = 0
t2 = 0
t3 = 0
while n:
    n -= 1

    x, s = input().split()

    x = int(x)
    if s == 'C':
        t1 += x

    elif s == 'R':
        t2+=x
    else:
        t3+=x

    total += x


print("Total: %d cobaias" % total)
print("Total de coelhos: %d" % t1)
print("Total de ratos: %d" % t2)
print("Total de sapos: %d" % t3)
print("Percentual de coelhos: %.2f %%" % ((t1/total) * 100))
print("Percentual de ratos: %.2f %%" % ((t2/total) * 100))
print("Percentual de sapos: %.2f %%" % ((t3/total) * 100))
