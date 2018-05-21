for i in 0 1 2 3 4 5 6 7 8
do
    diff arq0${i}.out arq0${i}.parte1.res
    echo $i
done

for i in 10
do
    diff arq${i}.out arq${i}.parte1.res
done

