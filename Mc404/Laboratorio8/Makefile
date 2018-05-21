compilacao:
	arm-eabi-gcc mapa.c -S -o mapa.s
montagem: compilacao
	arm-eabi-as mapa.s -g -o mapa.o
	arm-eabi-as dfs.s -g -o dfs.o
ligador: montagem
	arm-eabi-ld mapa.o dfs.o -g -o program -Ttext=0x77802000 -Tdata=0x77803000
criarSD: ligador 
	mksd.sh --so /home/specg12-1/mc404/simulador/dummyos.elf --user program
boot: criarSD 
	arm-sim --rom=/home/specg12-1/mc404/simulador/dumboot.bin --sd=disk.img -g 
gdb: 
	arm-eabi-gdb program
output: criarSD 
	(arm-sim --rom=/home/specg12-1/mc404/simulador/dumboot.bin --sd=disk.img 2>&1) > ra172358.out 
clean:
	rm disk.img mapa.o dfs.o program ra172358.out 
