#!/bin/bash

APP_PID=`ps -ef|grep -v grep| grep autoJob| awk '{print $2}'`
kill -9 $APP_PID
echo done
