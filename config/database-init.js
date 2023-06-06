print('Start #################################################################');

db = db.getSiblingDB('test');
db.createUser(
    {
        user: 'admin',
        pwd: 'password',
        roles: [{ role: 'readWrite', db: 'test' }],
    },
);
print('END #################################################################');