echo Executing GDB with .gdbinit to connect to OpenOCD.\n
echo .gdbinit is a hidden file. Press Ctrl-H in the current working directory to see it.\n
# Connect to OpenOCD
target remote localhost:5000
b _start
c
