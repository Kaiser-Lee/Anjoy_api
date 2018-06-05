#!/bin/bash

services=(schedule)
#echo  $services

ps -aux |egrep "(schedule.jar)"

ps -aux |egrep "(schedule.jar)" | cut -c 9-15 | xargs kill -9
