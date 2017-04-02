SHELL=/bin/bash
BUTLER ?= tools/butler/butler.py

TEAM 	= yaka
THEME   = yaka2018

include tools/butler/samples/template.mk

all:: subject.pdf slide.pdf clean

subject.pdf: DOCTYPE=subject
subject.pdf: DOCNAME=Subject
subject.pdf: BUTLERFLAGS+= --tree=subjects/tree.yml
subject.pdf: subjects/subject.pdf

slide.pdf: DOCTYPE=slide
slide.pdf: DOCNAME=Slide
slide.pdf: slides/slide.pdf

clean: subjects/subject.clean
clean: slides/slide.clean

distclean: clean
	${RM} subjects/*.pdf slides/*.pdf

.PHONY: all clean distclean
