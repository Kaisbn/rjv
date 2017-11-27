import butler

extensions = butler.extensions

acu = {
    'theme': 'yaka2019',
}

exclude_patterns = ['slides/*', 'server/*']

master_doc = 'index'

latex_documents = [
    butler.subject('subject', master_doc, 'project.yml')
]
