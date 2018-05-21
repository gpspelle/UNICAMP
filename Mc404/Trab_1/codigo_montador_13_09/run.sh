make clean
make montador.x
for i in 0 1 2 3 4 5 6 7 8 9
do
    ./montador.x testes-abertos_v2/arq0${i}.in  &> testes-abertos_v2/arq0${i}.out
done

for i in 10 11 12 13 14 15 
do
    ./montador.x testes-abertos_v2/arq${i}.in  &> testes-abertos_v2/arq${i}.out
done

for i in 0 1 2 3 4 5 6 7 8 9 
do
    ./montador.x cases/arq0${i}.in &> cases/arq0${i}.out
done

for i in 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 
do
    ./montador.x cases/arq${i}.in  &> cases/arq${i}.out
done
