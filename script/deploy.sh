cd /tmp/
APP_NAME="autoJob"
APP_NAME_LON=${APP_NAME,,}
APP_VERSION="0.0.2"
APP_HOME="/ec/apps/${APP_NAME_LON}"
BACKUP_HOME="/ec/apps/bak/${APP_NAME_LON}"

mkdir -p $BACKUP_HOME
rm -rf ${APP_NAME}-${APP_VERSION}
unzip ${APP_NAME}-${APP_VERSION}-distribution.zip


#backup
if [[ -d ${APP_HOME} ]]; then
    BACKUPED_FILE=$BACKUP_HOME/${APP_NAME_LON}-`date +%Y-%m-%d_%H%M%S`
    echo backup ${APP_HOME} to $BACKUPED_FILE
    cp -rf $APP_HOME $BACKUPED_FILE
    rm -rf $APP_HOME/lib
fi

#Stop 
echo stop ${APP_NAME}
${APP_HOME}/script/stop.sh

#Deploy sources
mv ./${APP_NAME}-${APP_VERSION}/lib $APP_HOME/

#Start 
echo start ${APP_NAME}
${APP_HOME}/script/start.sh