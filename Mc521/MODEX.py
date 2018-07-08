def get(x, y, n):
    if y < 10: 
        return (x ** y) % n
    else:
        if y % 2 == 0:
            return get(x, y//2, n) ** 2
        else:
            return ((get(x, y//2, n) ** 2) * (x % n)%n)

v = int(input())
for i in range(0, v):
    x, y, n = map(int, input().split())

    #aux = (x ** y) % n
    #print(aux)
    aux = get(x, y, n) % n

    print(aux)
