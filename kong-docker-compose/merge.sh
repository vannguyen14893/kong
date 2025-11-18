#!/bin/sh
echo "_format_version: \"3.0\"" > /kong/declarative/kong.yml
echo "_transform: true" >> /kong/declarative/kong.yml

########################
#       SERVICES
########################
echo "services:" >> /kong/declarative/kong.yml

for f in /kong/declarative/kong-config/services/*.yml; do
  sed 's/^/    /' "$f" >> /kong/declarative/kong.yml
  echo "" >> /kong/declarative/kong.yml
done

########################
#       CONSUMERS
########################
echo "consumers:" >> /kong/declarative/kong.yml

for f in /kong/declarative/kong-config/consumers/*.yml; do
  sed 's/^/    /' "$f" >> /kong/declarative/kong.yml
  echo "" >> /kong/declarative/kong.yml
done

########################
#        ROUTES
########################
echo "routes:" >> /kong/declarative/kong.yml

for f in /kong/declarative/kong-config/routes/*.yml; do
  sed 's/^/    /' "$f" >> /kong/declarative/kong.yml
  echo "" >> /kong/declarative/kong.yml
done

########################
#       PLUGINS
########################
echo "plugins:" >> /kong/declarative/kong.yml

for f in /kong/declarative/kong-config/plugins/*.yml; do
  sed 's/^/    /' "$f" >> /kong/declarative/kong.yml
  echo "" >> /kong/declarative/kong.yml
done

echo "DONE merging!"