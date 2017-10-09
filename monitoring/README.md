# Monitoring

A simple Grafana dashboard connected to a Prometheus master.

## How-To Deploy

Launch the Prometheus master and grafana, then import all Grafana dashboard
(the `.json` files) and configure Grafana to fetch its data from Prometheus.

```console
$ docker-compose up -d
```

To deploy the Prometheus collector, please see the Ansible Playbooks.
