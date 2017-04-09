SHELL=/bin/bash
BUTLER ?= tools/butler/butler.py

TEAM 	= yaka
THEME   = yaka2018

include tools/butler/samples/template.mk

EXERCICE_NAME = ex_battleship

all:: subject.pdf slides.pdf clean given-files

subject.pdf: DOCTYPE=subject
subject.pdf: DOCNAME=Subject
subject.pdf: BUTLERFLAGS+= --tree=subjects/tree.yml
subject.pdf: subjects/subject.pdf

slides.pdf: DOCTYPE=slide
slides.pdf: DOCNAME=Slides
slides.pdf: slides/slides.pdf

given-files:
	tar -jcvf files/creeps.tar.bz2 -C files ./creeps

clean: subjects/subject.clean
clean: slides/slides.clean

distclean: clean
	${RM} subjects/*.pdf slides/*.pdf files/creeps.tar.bz2

.PHONY: all clean distclean given-files
