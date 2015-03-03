###########################
#
# Executes the AdSearch Server
# e.g. server.sh start
#!/usr/bin/env bash


abspath=$(cd ${0%/*} && echo $PWD/${0##*/})
BIN_HOME=`dirname $abspath`

export ADSEARC_HOME=$BIN_HOME/../

export CONF_DIR=$ADSEARC_HOME/conf

export LOG_DIR=$ADSEARC_HOME/logs

#source environment variables

# some Java parameters
if [ "$JAVA_HOME" != "" ]; then
    #echo "run java in $JAVA_HOME"
   JAVA_HOME=$JAVA_HOME
fi

if [ "$JAVA_HOME" = "" ]; then
     echo "Error: JAVA_HOME is not set."
     exit 1
fi

JAVA=$JAVA_HOME/bin/java


if [ -z $JAVA_HEAP ]; then
 export JAVA_HEAP="-Xmx1024m"
fi

# check envvars which might override default args
# CLASSPATH initially contains $CONF_DIR
CLASSPATH=${CLASSPATH}:$JAVA_HOME/lib/tools.jar

# for developers, add Pig classes to CLASSPATH

# so that filenames w/ spaces are handled correctly in loops below
IFS=
# add libs to CLASSPATH.
CLASSPATH="${CLASSPATH}:$ADSEARC_HOME/lib/*"

CLASS="com.shuja.redis.StartTest"

CLASSPATH="$CONF_DIR:$CONF_DIR/META-INF:$CLASSPATH"

exec nohup "$JAVA" -da -server -XX:+AggressiveOpts -XX:BiasedLockingStartupDelay=0 -XX:MaxDirectMemorySize=6g -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+DisableExplicitGC $JAVA_HEAP $JAVA_OPTS -classpath "$CLASSPATH" $CLASS "$@" "$CONF_DIR"
