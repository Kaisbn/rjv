# Ansible Playbooks

This Ansible Playbooks ease the deployment of the infrastructure required
for the rush.

There are many playbooks, each with a specific usage:
- `setup.yml`: configure the servers and deploy a minecraft server, configured
  to restart every 60 minutes. It also deploy all the Prometheus collector.
- `client.yml`: deploy many IAs (see the roles `client` for configuration). It
  should be used to test the infrastructure (and so define how many servers will
  be required for the rush).
- `monitoring.yml`: deploy to the manager some generated JSON with the list of
  servers. It is used by the dashboard and Prometheus master to fetch all the
  metrics from the servers.
- `poweroff.yml`: poweroff all servers.
- `cache.yml`: refresh the cache of Ansible.

**Notes: please run `server/tarball.sh` before deploying the minecraft server.**
