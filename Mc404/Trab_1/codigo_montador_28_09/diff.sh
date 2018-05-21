cd testes-abertos_v2/
t=0
z=0
for i in 0 1 2 3 4 5 6 7 8 9
do
    var=$(diff arq0${i}.out arq0${i}.parte1.res);
    if [ "$var" -gt "$z" ]; then
        let "t=t+1"
    fi
done

echo $t 
