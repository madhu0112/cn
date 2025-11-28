# ----------------------------
# Create Simulator
# ----------------------------
set ns [new Simulator]

# Use colors to differentiate the traffic
$ns color 1 Blue
$ns color 2 Red

# Open trace and NAM trace file
set ntrace [open prog3.tr w]
$ns trace-all $ntrace

set namfile [open prog3.nam w]
$ns namtrace-all $namfile

# ----------------------------
# Finish Procedure
# ----------------------------
proc Finish {} {
    global ns ntrace namfile

    # Dump all trace data and close files
    $ns flush-trace
    close $ntrace
    close $namfile

    # Execute the NAM animation
    exec nam prog3.nam &

    # Print number of dropped ping packets
    puts "The number of ping packets dropped are:"
    exec grep "^d" prog3.tr | cut -d " " -f 5 | grep -c "ping" &

    exit 0
}

# ----------------------------
# Create six nodes
# ----------------------------
for {set i 0} {$i < 6} {incr i} {
    set n($i) [$ns node]
}

# ----------------------------
# Connect the nodes in a chain
# ----------------------------
for {set j 0} {$j < 5} {incr j} {
    $ns duplex-link $n($j) $n([expr $j+1]) 0.1Mb 10ms DropTail
}

# ----------------------------
# Override the recv function for Agent/Ping
# ----------------------------
Agent/Ping instproc recv {from rtt} {
    $self instvar node_
    puts "Node [$node_ id] received ping reply from $from with RTT = $rtt ms"
}

# ----------------------------
# Create two ping agents and attach to n(0) and n(5)
# ----------------------------
set p0 [new Agent/Ping]
$p0 set class_ 1
$ns attach-agent $n(0) $p0

set p1 [new Agent/Ping]
$p1 set class_ 1
$ns attach-agent $n(5) $p1

$ns connect $p0 $p1

# ----------------------------
# Queue settings between n2 and n3
# ----------------------------
$ns queue-limit $n(2) $n(3) 2
$ns duplex-link-op $n(2) $n(3) queuePos 0.5

# ----------------------------
# Create TCP–CBR congestion traffic between n2 and n4
# ----------------------------
set tcp0 [new Agent/TCP]
$tcp0 set class_ 2
$ns attach-agent $n(2) $tcp0

set sink0 [new Agent/TCPSink]
$ns attach-agent $n(4) $sink0

$ns connect $tcp0 $sink0

# CBR application
set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set rate_ 1Mb
$cbr0 attach-agent $tcp0

# ----------------------------
# Schedule Events
# ----------------------------
$ns at 0.2 "$p0 send"
$ns at 0.4 "$p1 send"
$ns at 0.4 "$cbr0 start"

$ns at 0.8 "$p0 send"
$ns at 1.0 "$p1 send"

$ns at 1.2 "$cbr0 stop"

$ns at 1.4 "$p0 send"
$ns at 1.6 "$p1 send"

$ns at 1.8 "Finish"

# ----------------------------
# Run the simulation
# ----------------------------
$ns run

