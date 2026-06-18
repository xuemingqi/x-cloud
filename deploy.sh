#!/bin/bash
#启动任意多个jar
start() {
  USAGE='Usage: start <env> <project path>'
  env=$1
  shift
  for project in "$@"; do
    if [[ ! $project ]]; then
      echo "specify the project！"
      exit 1
    fi
    pid=$(ps ux | grep $project | grep java | awk '{print $2}')
    if [[ $port_0 ]]; then
      pid=$(ps ux | grep $port_0 | grep $project | grep java | awk '{print $2}')
    fi
    if [[ $pid ]]; then
      echo "[$project] is running!"
    else
      if nohup java -Dspring.profiles.active=$env -Dfile.encoding=utf-8 -jar $project >/dev/null 2>&1 & then
        echo "[$project] is starting with environment [$env]!"
      fi
    fi
  done
}

#kill掉任意多个jar
killJar() {
  USAGE='Usage: kill <project path>'
  for project in "$@"; do
    if [[ ! $project ]]; then
      echo "specify the project！"
      exit 1
    fi
    pid=$(ps ux | grep $project | grep java | awk '{print $2}')
    if [[ $port_0 ]]; then
      pid=$(ps ux | grep $port_0 | grep $project | grep java | awk '{print $2}')
    fi
    if $(kill -9 $pid); then
      echo "project $project PID $pid is stopped!"
    fi
  done
}

#启动指定目录下所有jar包，默认启动目录下所有jar包
native() {
  USAGE='Usage: native <env> <project path>'
  env=$1
  projectdir=$2
  if [[ ! $projectdir ]]; then
    echo "specify the project path！"
    exit 1
  fi
  cd $projectdir || exit
  for name in $(ls -rt *.jar); do
    PID=$(ps ux | grep $name | grep java | awk '{print $2}')
    if [[ -n $PID ]]; then
      echo "[$name] is running!"
    else
      if nohup java -Dspring.profiles.active=$env -Dfile.encoding=utf-8 -jar ./$name >/dev/null 2>&1 & then
        echo "[$name] is starting with environment [$env]!"
      fi
    fi
  done
  exit 0
}

#kill掉当前目录下所有jar包名字的进程
stop() {
  for name in $(ls -rt *.jar); do
    PID=$(ps ux | grep $name | grep java | awk '{print $2}')
    if $(kill -9 $PID); then
      echo "PID $PID is stopped!"
    fi
  done
}

case "$1" in
"start")
  shift
  start "$@"
  ;;
"kill")
  shift
  killJar "$@"
  ;;
"native")
  shift
  native "$@"
  ;;
"stop")
  shift
  stop "$@"
  ;;
*)
  usage
  ;;
esac
exit
