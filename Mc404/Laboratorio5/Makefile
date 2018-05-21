montagem:
	arm-eabi-as modelo.s -g -o modelo.o
ligador: montagem
	arm-eabi-ld modelo.o -g -o modelo -Ttext=0x77802000 -Tdata=0x77803000
criarSD: ligador 
	mksd.sh --so /home/specg12-1/mc404/simulador/dummyos.elf --user modelo
boot: criarSD 
	arm-sim --rom=/home/specg12-1/mc404/simulador/dumboot.bin --sd=disk.img -g 
execute:
	arm-eabi-gdb modelo
output:
	(arm-sim --rom=/home/specg12-1/mc404/simulador/dumboot.bin --sd=disk.img 2>&1) > ra172358.out 
