source /home/specg12-1/mc404/simulador/set_path.sh 
if [ $1 == 0 ]
then
    make compilacao
elif [ $1 == 1 ]
then
    make montagem
elif [ $1 == 2 ]
then
    make ligador
elif [ $1 == 3 ]
then
    make criarSD
elif [ $1 == 4 ]
then 
    make boot
elif [ $1 == 5 ]
then
    make output
elif [ $1 == 6 ]
then
    make gdb
fi

