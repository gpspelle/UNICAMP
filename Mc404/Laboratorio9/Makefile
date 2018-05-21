montagem:
	arm-eabi-as motors.s -o motors.o
ligador: montagem
	arm-eabi-ld motors.o -o motors -Ttext=0x77802000
criarSD: ligador 
	mksd.sh --so /home/specg12-1/mc404/simulador/simulador_player/bin/knrl --user motors
boot: criarSD 
	player /home/specg12-1/mc404/simulador/simulador_player/worlds_mc404/simple.cfg
simular: criarSD 
	armsim_player --rom=/home/specg12-1/mc404/simulador/simulador_player/bin/dumboot.bin --sd=disk.img
clean:
	rm disk.img mapa.o dfs.o program ra172358.out 
gdb: 
	arm-eabi-gdb program
