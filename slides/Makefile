TARGET = main.pdf

RM         ?= rm -f
MPP         = /sgoinfre/qualite/mpp/src/mpp.py
MPPFLAGS    =
PANDOC      = pandoc
PANDOCTMPL  =  \
        /sgoinfre/qualite/butler/templates/yaka/en/yaka_en_slides.tex
PANDOCFLAGS = --slide-level=3 --latex-engine=xelatex --listings --toc \
	      --template=${PANDOCTMPL} -V lang=english

DEPS := $(shell ${MPP} --list-includes ${TARGET:.pdf=.md})

all: ${IMAGES} ${TARGET}

clean:
	${RM} ${TARGET}

${TARGET}: ${DEPS}

.svg.pdf:
	rsvg-convert -f pdf -o $@ $<

.md.mppmd:
	${MPP} ${MPPFLAGS} $< > $@

.mppmd.pdf:
	${PANDOC} ${PANDOCFLAGS} -t beamer -o $@ $<

.SUFFIXES: .md .mppmd .pdf .svg

#EOF
