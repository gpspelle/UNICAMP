t = int(input())

l = []

a1 =  ['', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine', 'ten']
a2 = ['eleven', 'twelve', 'thirteen', 'fourteen', 'fifteen', 'sixteen', 'seventeen', 'eighteen', 'nineteen']
a3 = ['twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety']
a4 = ['onehundred', 'twohundred', 'threehundred', 'fourhundred', 'fivehundred', 'sixhundred', 'sevenhundred', 'eighthundred', 'ninehundred']

dicionario = []
dicionario += a1
dicionario += a2

numeros = dict()
for i in range(0, len(a1)):
    numeros[a1[i]] = i 

for i in range(0, len(a2)):
    numeros[a2[i]] = 10 + i + 1

for i in range(0, len(a3)):
    numeros[a3[i]] = 10*(i+2)

for i in range(0, len(a4)):
    numeros[a4[i]] = 100*(i+1)
    
for i in a3:
    for x in range(10):
        dicionario.append(i + dicionario[x])
        numeros[i+dicionario[x]] = numeros[i] + numeros[dicionario[x]]

for i in a4:
    for x in range(100):
        dicionario.append(i + dicionario[x])
        numeros[i + dicionario[x]] = numeros[i] + numeros[dicionario[x]]

tam = 0
tamanho = dict()
for x in dicionario:
    tamanho[x] = len(x)

for i in range(t):
    k = input()
    tam += len(k)
    l.append(k)
    
tam -= 1

for k in dicionario:
    if k == '':
        continue
    if tamanho[k] + tam == numeros[k]:
        resp = ' '.join(l)
        print(resp.replace('$', k))
        break

