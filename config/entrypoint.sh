#!/bin/bash

# Start MongoDB in the background
mongod --replSet rs0 --bind_ip_all --fork --logpath /var/log/mongodb.log

# Wait for MongoDB to start
sleep 5

# Initialize the replica set if it's not already initiated
if ! mongosh --quiet --eval "rs.status().ok" > /dev/null; then
  mongosh --eval 'config = {
      "_id": "rs0",
      "members": [
          {
              "_id": 0,
              "host": "127.0.0.1:27017"
          }
      ]
  }; rs.initiate(config);'
fi

# Stop the background MongoDB process
mongod --shutdown

# Start MongoDB in the foreground
exec mongod --replSet rs0 --bind_ip_all