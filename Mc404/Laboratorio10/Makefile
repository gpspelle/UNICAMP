montagem:
	arm-eabi-as -g ra172358.s -o ra172358.o
ligador: montagem
	arm-eabi-ld ra172358.o -o ra172358 -g --section-start=.iv=0x778005e0 -Ttext=0x77800700 -Tdata=0x77801800 -e 0x778005e0
criarSD: ligador 
	mksd.sh --so ra172358 --user faz_nada
boot:
	player /home/specg12-1/mc404/simulador/simulador_player/worlds_mc404/simple.cfg	
simular: criarSD 
	armsim_player --rom=/home/specg12-1/mc404/simulador/simulador_player/bin/dumboot.bin --sd=disk.img -g
gdb: 
	arm-eabi-gdb ra172358
