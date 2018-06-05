#!/bin/bash

services=(dms)
#echo  $services

ps -aux |egrep "(dms.jar)"

ps -aux |egrep "(dms.jar)" | cut -c 9-15 | xargs kill -9
