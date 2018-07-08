m, n = map(int, input().split())

u, l, r, d = map(int, input().split())

cross= []
for i in range(m):
    cross.append(input())

matrix = [ [0 for x in range(n+l+r)] for y in range(u+m+d) ]
x = 0
col_x = 0
for i in range(u + d + m):
    x = col_x
    for j in range(n + l + r):
        if x == 0:
            matrix[i][j] = '#'
        else:
            matrix[i][j] = '.'

        x = 1 - x
    col_x = 1 - col_x

for i in range(u, u+m):
    for j in range(l, l+n):
        matrix[i][j] = cross[i-u][j-l]

for x in matrix:
    print(''.join(x))
