SHELL=/bin/bash
BUTLER ?= tools/butler/butler.py

TEAM 	= yaka
THEME   = yaka2018

include tools/butler/samples/template.mk

all:: subject.pdf clean

subject.pdf: DOCTYPE=subject
subject.pdf: DOCNAME=Subject
subject.pdf: BUTLERFLAGS+= --tree=subjects/tree.yml
subject.pdf: subjects/subject.pdf

clean: subjects/subject.clean

distclean: clean
	${RM} *.pdf subjects/*.pdf

.PHONY: all clean distclean
