for i in 0 1 2 3 4 5 6 7 8 9
do
    var=$(diff arq0${i}.out arq0${i}.res)
    echo $var
    echo $i
done

for i in 10 11 12 13 14 15 
do
    var=$(diff arq${i}.out arq${i}.res)
    echo $var
    echo $i
done
