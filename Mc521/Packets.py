import math

while True:
    p1, p2, p3, p4, p5, p6 = map(int, input().split())

    if p1 + p2 + p3 + p4 + p5 + p6 <= 0:
        break

    total = 0
    total += p6 # put all p6 in a separated parcel

    total += p5
    p1 -= 11 * p5 # put all p1 boxes together with p5 boxes

    total += p4
    p2 -= 5 * p4 # put all p2 boxes together with p5 boxes

    total += math.ceil(p3/4)

    if p3 % 4 == 3:
        p2 -= 1;
        p1 -= 5;

    elif p3 % 4 == 2:
        p2 -= 3;
        p1 -= 6;

    elif p3 % 4 == 1:
        p2 -= 5;
        p1 -= 7;

    if p2 > 0:
        total += math.ceil(p2/9)
        p2 -= 9 * math.ceil(p2/9)

    if p2 < 0:
        p1 += p2 * 4

    if p1 > 0:
        total += math.ceil(p1/36)

    print(total)
