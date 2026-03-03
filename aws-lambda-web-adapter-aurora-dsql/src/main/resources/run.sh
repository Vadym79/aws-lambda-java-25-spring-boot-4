#!/bin/sh

exec java -cp "./:lib/*" "software.amazonaws.Application" -Dlogging.level.org.springframework=DEBUG